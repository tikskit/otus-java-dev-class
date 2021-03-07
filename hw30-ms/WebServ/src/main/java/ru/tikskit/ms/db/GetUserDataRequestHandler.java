package ru.tikskit.ms.db;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.core.dao.UserDao;
import ru.otus.core.model.User;
import ru.otus.core.service.DBServiceUser;
import ru.otus.core.service.DbServiceException;
import ru.otus.messagesystem.RequestHandler;
import ru.otus.messagesystem.message.Message;
import ru.otus.messagesystem.message.MessageBuilder;
import ru.otus.messagesystem.message.MessageHelper;
import ru.tikskit.ms.UserData;

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
            User userObj = jsonToUser(userData.getData());
            try {
                long id = dbService.saveUser(userObj);
                userData = new UserData(id, userToJson(userObj));
                //sendNewUser(userObj);
                return Optional.of(MessageBuilder.buildReplyMessage(msg, userData));
            } catch (DbServiceException e) {
                logger.error("Error on creation user", e);
            }
        } catch (JsonProcessingException e) {
            logger.error("Error on convertion json to User", e);
        }

/*
        //Получаем идентификатор юзера, для которого мы хотим получить данные - userData.getUserId()
        UserData userData = MessageHelper.getPayload(msg);
        // Получаем некую UserData для полученного идентификатора
        Optional<User> user = dbService.getUser(userData.getUserId());
        logger.info("getting user from db: {}", userData.getUserId());
        if (user.isPresent()) {
            UserData data = null;
            try {
                data = new UserData(userData.getUserId(), jsonToStr(user.get()));
            } catch (JsonProcessingException e) {
                logger.error("Error on convertion User to JSON", e);
                return Optional.empty();
            }
            return Optional.of(MessageBuilder.buildReplyMessage(msg, data));
        } else {
            logger.warn("User {} wasn't found in database", userData.getUserId());
            return Optional.empty();
        }
*/
    }

    private User jsonToUser(String json) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(json, User.class);
    }
    private String userToJson(User user) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(user);
    }
}
