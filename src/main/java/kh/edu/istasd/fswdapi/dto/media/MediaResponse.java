package kh.edu.istasd.fswdapi.dto.media;

import lombok.Builder;

@Builder
public record MediaResponse(
        String name,
        String mimeType,
        String uri,
        Long size
) {
}
