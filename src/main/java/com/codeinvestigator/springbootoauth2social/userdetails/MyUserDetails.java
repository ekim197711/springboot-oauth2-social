package com.codeinvestigator.springbootoauth2social.userdetails;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MyUserDetails {
    private String picture;
    private String name;
    private String email;
}
