package com.example.query;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Accessors(chain = true)
public class MeetingExcel {

  private String meetingName;

  private String meetingContent;

  private String meetingPerson;

  private LocalDateTime meetingStart;

  private LocalDateTime meetingEnd;

  private String host;

}
