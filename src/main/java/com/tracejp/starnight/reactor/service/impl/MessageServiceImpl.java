package com.tracejp.starnight.reactor.service.impl;

import com.tracejp.starnight.reactor.dao.MessageDao;
import com.tracejp.starnight.reactor.entity.MessageEntity;
import com.tracejp.starnight.reactor.service.IMessageService;
import org.springframework.stereotype.Service;

/**
 * <p>  <p/>
 *
 * @author traceJP
 * @since 2023/9/14 16:56
 */
@Service
public class MessageServiceImpl extends BaseService<MessageDao, MessageEntity> implements IMessageService {
}
