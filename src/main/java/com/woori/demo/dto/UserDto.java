package com.woori.demo.dto;

import com.woori.demo.domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class UserDto {
    private long user_key;
    private String name;
    private String email;
    private String password;
    private String address1;
    private String address2;
    private String zipcode;
    private boolean activated;
    private LocalDateTime createdAt;

    private Set<AuthorityDto> authorityDtoSet;

    public static UserDto from(User user) {
        if(user == null) return null;

        return UserDto.builder()
                .user_key(user.getUser_key())
                .name(user.getName())
                .email(user.getEmail())
                .address1(user.getAddress1())
                .address2(user.getAddress2())
                .zipcode(user.getZipcode())
                .createdAt(user.getCreatedAt())
                .authorityDtoSet(user.getAuthorities().stream()
                        .map(authority -> AuthorityDto.builder().authorityName(authority.getAuthorityName()).build())
                        .collect(Collectors.toSet()))
                .build();
    }
}
