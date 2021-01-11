package peter.finalprojectparallel.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import peter.finalprojectparallel.dto.ChannelDto;
import peter.finalprojectparallel.dto.UserDto;
import peter.finalprojectparallel.model.User;
import peter.finalprojectparallel.service.ChannelService;

import java.util.List;

@RestController
@RequestMapping("/api/channel")
@AllArgsConstructor
@Slf4j
public class ChannelController {

    private final ChannelService channelService;

    @PostMapping
    public ResponseEntity<ChannelDto> createChannel(@RequestBody ChannelDto channelDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(channelService.save(channelDto));
    }

    @GetMapping
    public ResponseEntity<List<ChannelDto>> getAllChannels() {
        return ResponseEntity.status(HttpStatus.OK).body(channelService.getAllChannels());
    }

    @GetMapping("/{channelId}")
    public ResponseEntity<ChannelDto> getChannelById(@PathVariable Long channelId) {
        return ResponseEntity.status(HttpStatus.OK).body(channelService.getChannelById(channelId));
    }

    @DeleteMapping("/deleteChannel/{channelId}")
    public ResponseEntity deleteChannel(@PathVariable Long channelId) {
        return ResponseEntity.status(HttpStatus.OK).body(channelService.deleteById(channelId));
    }

    @PostMapping("/{channelId}/addUser/{userId}")
    public ResponseEntity<ChannelDto> addUserToChannel(@PathVariable Long channelId, @PathVariable Long userId) {
        return ResponseEntity.status(HttpStatus.OK).body(channelService.addUserById(channelId, userId));
    }

}
