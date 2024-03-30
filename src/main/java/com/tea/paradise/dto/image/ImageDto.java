package com.tea.paradise.dto.image;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ImageDto {
    private Long id;
    private byte[] image;
    private String fileName;
}
