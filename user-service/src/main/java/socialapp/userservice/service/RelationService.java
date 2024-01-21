package socialapp.userservice.service;

import socialapp.userservice.model.dto.UserRelationsDto;

public interface RelationService {

    void deleteBlock(Long id);

    void deleteSubscription(Long id);

    void deleteFriendship(Long id, Long by);

    void blockUser(Long sender,Long receiver);

    void subscribeUser(Long sender,Long receiver);

    void addFriendship(Long sender,Long receiver);

    UserRelationsDto findUserRelations(Long userId);
}
