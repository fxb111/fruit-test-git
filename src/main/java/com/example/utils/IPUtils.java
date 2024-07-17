package com.example.utils;

import jakarta.annotation.PostConstruct;
import org.lionsoul.ip2region.xdb.Searcher;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.server.ServerWebExchange;


import java.io.InputStream;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;

@Component
public class IPUtils {
  private static Searcher searcher;

  /**
   * 在 Nginx 等代理之后获取用户真实 IP 地址
   * @return 用户的真实 IP 地址
   */
  public static String getIpAddress(ServerWebExchange request) {
    if (request == null) {
      return null;
    }
    String ip = request.getRequest().getHeaders().getFirst("x-forwarded-for");
    if (isIpaddress(ip)) {
      ip = request.getRequest().getHeaders().getFirst("Proxy-Client-IP");
    }
    if (isIpaddress(ip)) {
      ip = request.getRequest().getHeaders().getFirst("WL-Proxy-Client-IP");
    }
    if (isIpaddress(ip)) {
      ip = request.getRequest().getHeaders().getFirst("HTTP_CLIENT_IP");
    }
    if (isIpaddress(ip)) {
      ip = request.getRequest().getHeaders().getFirst("HTTP_X_FORWARDED_FOR");
    }
    if (isIpaddress(ip)) {
      ip = request.getRequest().getRemoteAddress().getAddress().getHostAddress();
      if ("127.0.0.1".equals(ip) || "0:0:0:0:0:0:0:1".equals(ip)) {
        //根据网卡取本机配置的IP
        try {
          InetAddress inet = InetAddress.getLocalHost();
          ip = inet.getHostAddress();
        } catch (UnknownHostException e) {
          e.printStackTrace();
        }
      }
    }
    return ip;
  }

  /**
   * 判断是否为 IP 地址
   * @param ip  IP 地址
   */
  public static boolean isIpaddress(String ip) {
    return ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip);
  }

  /**
   * 获取本地 IP 地址
   * @return 本地 IP 地址
   */
  public static String getHostIp() {
    try {
      return InetAddress.getLocalHost().getHostAddress();
    } catch (UnknownHostException e) {
      e.printStackTrace();
    }
    return "127.0.0.1";
  }

  /**
   * 获取主机名
   * @return 本地主机名
   */
  public static String getHostName() {
    try {
      return InetAddress.getLocalHost().getHostName();
    } catch (UnknownHostException e) {
      e.printStackTrace();
    }
    return "未知";
  }

  /**
   * 根据 IP 地址从 ip2region.db 中获取地理位置
   * @param ip IP 地址
   * @return IP归属地
   */
  public static String getCityInfo(String ip) {
    try {
      return searcher.search(ip);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }

  /**
   * 在服务启动时加载 ip2region.db 到内存中
   * 解决打包 jar 后找不到 ip2region.db 的问题
   * @throws Exception 出现异常应该直接抛出终止程序启动，避免后续 invoke 时出现更多错误
   */
  @PostConstruct
  private static void initIp2regionResource() {
    try {
      InputStream inputStream = new ClassPathResource("/ip2region/ip2region.xdb").getInputStream();
      byte[] dbBinStr = FileCopyUtils.copyToByteArray(inputStream);
      // 创建一个完全基于内存的查询对象
      searcher = Searcher.newWithBuffer(dbBinStr);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * 根据 IP 地址返回归属地，国内返回但省份，国外返回到国家
   * @param ip IP 地址
   * @return IP 归属地
   */
  public static String getIpRegion(String ip) {
    initIp2regionResource();
    HashMap<String, String> cityInfo = new HashMap<>();
    String searchIpInfo = getCityInfo(ip);
    String[] splitIpInfo = searchIpInfo.split("\\|");
    cityInfo.put("ip",ip);
    cityInfo.put("searchInfo", searchIpInfo);
    cityInfo.put("country",splitIpInfo[0]);
    cityInfo.put("region",splitIpInfo[1]);
    cityInfo.put("province",splitIpInfo[2]);
    cityInfo.put("city",splitIpInfo[3]);
    cityInfo.put("ISP",splitIpInfo[3]);

    //--------------国内属地返回省份--------------
    if ("中国".equals(cityInfo.get("country"))){
      return cityInfo.get("province") + cityInfo.get("city");
    }
    //------------------内网 IP----------------
    if ("0".equals(cityInfo.get("country"))){
      if ("内网IP".equals(cityInfo.get("ISP"))){
        return "内网ip";
      }
      else return "未知ip";
    }
    //--------------国外属地返回国家--------------
    else {
      return cityInfo.get("country");
    }
  }
}

