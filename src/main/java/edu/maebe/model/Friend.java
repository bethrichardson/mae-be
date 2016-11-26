package edu.maebe.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

import java.util.Date;
import java.util.UUID;

@ToString(includeFieldNames=true)
@Data
@AllArgsConstructor
public class Friend {
    UUID id;
    String name;
    String nickname;
    String phone;
    String email;
    String userId;
    String type;
    Date date;

    public static final String FRIEND_TYPE_PHYSICIAN = "physician";
    public static final String FRIEND_TYPE_FRIEND = "friend";

    public Friend(String name, String nickname, String phone, String email, String userId, String type) {
        this.name = name;
        this.nickname = nickname;
        this.phone = phone;
        this.email = email;
        this.userId = userId;
        this.type = type;
    }
}
