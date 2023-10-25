package com.www.scheduleer.controller.dto.notification;

import com.www.scheduleer.controller.dto.command.DataType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class NotificationSaveDto {
    @NotBlank(message = "NOT_EMPTY_MESSAGE")
    private String message;

    @NotBlank(message = "NOT_EMPTY_TYPE")
    private DataType type;
}
