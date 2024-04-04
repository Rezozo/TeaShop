package com.tea.paradise.dto.saveDto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserSaveDto {
    @NotNull
    @NotEmpty
    private String name;
    @NotEmpty
    private String surname;
    @NotNull
    @NotEmpty
    private String email;
    private String password;
}
