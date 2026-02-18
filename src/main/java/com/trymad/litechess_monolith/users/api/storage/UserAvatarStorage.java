package com.trymad.litechess_monolith.users.api.storage;

import java.io.InputStream;

public interface UserAvatarStorage {

    String storeAvatar(String userId, InputStream content, long contentLength);

    void deleteAvatar(String userId);
	
}
