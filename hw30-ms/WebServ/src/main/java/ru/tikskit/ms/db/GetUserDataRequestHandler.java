package ru.tikskit.ms.db;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.core.model.User;
import ru.otus.core.service.DBServiceUser;
import ru.otus.core.service.DbServiceException;
import ru.otus.messagesystem.RequestHandler;
import ru.otus.messagesystem.message.Message;
import ru.otus.messagesystem.message.MessageBuilder;
import ru.otus.messagesystem.message.MessageHelper;
import ru.tikskit.ms.UserData;
import ru.tikskit.utils.UserUtils;

import java.util.Optional;


public class GetUserDataRequestHandler implements RequestHandler<UserData> {
    private static final Logger logger = LoggerFactory.getLogger(GetUserDataRequestHandler.class);
    private final DBServiceUser dbService;

    public GetUserDataRequestHandler(DBServiceUser dbService) {
        this.dbService = dbService;
    }

    @Override
    public Optional<Message> handle(Message msg) {
        // сохраняем пользователя в БД
        UserData userData = MessageHelper.getPayload(msg);
        try {
            User userObj = UserUtils.jsonToUser(userData.getData());
            try {
                long id = dbService.saveUser(userObj);
                logger.info("user saved to db: {}", id);
                userData = new UserData(id, UserUtils.userToJson(userObj));
                return Optional.of(MessageBuilder.buildReplyMessage(msg, userData));
            } catch (DbServiceException e) {
                logger.error("Error on user creation", e);
            }
        } catch (JsonProcessingException e) {
            logger.error("Error on convertion json to User", e);
        }
        return Optional.empty();
    }

}
