package edu.maebe.handlers;
import edu.maebe.Validable;
import lombok.Data;

@Data
class NewFriendPayload implements Validable {
    private String name;
    private String nickname;
    private String phone;
    private String email;
    private String userId;
    private String type;

    public boolean isValid() {
        return name != null && !name.isEmpty() && type != null && !type.isEmpty()
                && userId != null && !userId.isEmpty();
    }
}