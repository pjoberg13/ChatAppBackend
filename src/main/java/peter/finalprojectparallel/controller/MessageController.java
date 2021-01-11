package peter.finalprojectparallel.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import peter.finalprojectparallel.dto.MessageRequest;
import peter.finalprojectparallel.dto.MessageResponse;
import peter.finalprojectparallel.service.MessageService;

import java.util.List;

@RestController
@RequestMapping("/api/message")
@AllArgsConstructor
@Slf4j
public class MessageController extends TextWebSocketHandler {

    private final MessageService messageService;

    @CrossOrigin
    @PostMapping("/channel/{channelId}/message")
    public ResponseEntity<String> create(@RequestBody MessageRequest messageRequest, @PathVariable Long channelId){
        messageService.create(messageRequest, channelId);
        return ResponseEntity.status(HttpStatus.CREATED).body("message created");
    }

    @CrossOrigin
    @GetMapping("/byChannel/{channelId}")
    public ResponseEntity<List<MessageResponse>> getMessagesByChannel(@PathVariable Long channelId) {
        return ResponseEntity.status(HttpStatus.OK).body(messageService.getAllByChannel(channelId));
    }

    @CrossOrigin
    @MessageMapping("/chat/{channelId}")
    @SendTo("/channel/{channelId}")
    public ResponseEntity<Void> createWithSock(@RequestBody MessageRequest messageRequest, @DestinationVariable Long channelId) {
        messageService.create(messageRequest, channelId);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

}
