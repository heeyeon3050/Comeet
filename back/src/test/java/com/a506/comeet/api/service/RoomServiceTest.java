package com.a506.comeet.api.service;

import com.a506.comeet.member.entity.Member;
import com.a506.comeet.room.controller.RoomCreateRequestDto;
import com.a506.comeet.room.controller.RoomJoinRequestDto;
import com.a506.comeet.room.controller.RoomUpdateRequestDto;
import com.a506.comeet.common.enums.RoomConstraints;
import com.a506.comeet.common.enums.RoomType;
import com.a506.comeet.room.entity.Room;
import static org.assertj.core.api.Assertions.*;

import com.a506.comeet.room.repository.RoomRepository;
import com.a506.comeet.room.service.RoomService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Slf4j
@Transactional
@Rollback(value = false)
class RoomServiceTest {

    @Autowired
    private RoomService roomService;

    @Autowired
    private RoomRepository roomRepository;

    @PersistenceContext
    private EntityManager em;

//    @BeforeEach
//    void before(){
//        RoomCreateRequestDto req = RoomCreateRequestDto.builder().
//                mangerId("멤버1").
//                title("title").description("설명").capacity(10).constraints(RoomConstraints.FREE.get()).type(RoomType.DISPOSABLE.get()).
//                build();
//
//        Room room = roomService.createRoom(req);
//    }

    @Test
    void createTest(){
        Member manager = Member.builder().memberId("멤버1").build();
        em.persist(manager);
        em.flush();
        em.clear();

        RoomCreateRequestDto req = RoomCreateRequestDto.builder().
                mangerId("멤버1").
                title("title").description("설명").capacity(10).constraints(RoomConstraints.FREE.get()).type(RoomType.DISPOSABLE.get()).
                build();

        Room room = roomService.createRoom(req);
        assertThat(room.getTitle()).isEqualTo(req.getTitle());
        log.info("room id : {}", room.getId());
    }

    @Test
    void updateTest(){

        Member manager = Member.builder().memberId("멤버1").build();
        Member newManager = Member.builder().memberId("멤버2").build();
        em.persist(manager);
        em.persist(newManager);
        em.flush();
        em.clear();

        RoomCreateRequestDto req = RoomCreateRequestDto.builder().
                mangerId("멤버1").
                title("title").description("설명").capacity(10).constraints(RoomConstraints.FREE.get()).type(RoomType.DISPOSABLE.get()).
                build();

        Room room = roomService.createRoom(req);

        RoomUpdateRequestDto req2 = RoomUpdateRequestDto.builder().mangerId("멤버2").build();
        roomService.updateRoom(req2, room.getId());


        assertThat(room.getManager().getMemberId()).isEqualTo(req2.getMangerId());
        log.info("room manager Id : {}", room.getManager().getMemberId());
    }

    @Test
    @DisplayName("유저가 방에 가입한다")
    void joinTest(){
        //given
        // Manager 멤버 생성
        Member manager = Member.builder().memberId("멤버1").build();
        em.persist(manager);
        em.flush();
        em.clear();

        //방 생성
        RoomCreateRequestDto reqR = RoomCreateRequestDto.builder().
                mangerId(manager.getMemberId()).
                title("title").description("설명").capacity(10).constraints(RoomConstraints.FREE.get()).type(RoomType.PERMANENT.get()).
                build();
        Room newRoom = roomService.createRoom(reqR);
        // 생성된 방의 id
        Long roomId = newRoom.getId();

        // 가입할 멤버 생성
        Member member = Member.builder().memberId("member1").build();
        em.persist(member);
        em.flush();
        em.clear();

        // when
        // 멤버를 방에 가입시킴
        RoomJoinRequestDto req = new RoomJoinRequestDto("member1");
        roomService.joinMember(req, roomId);
        log.info("member의 roomMember : {}", member.getRoomMembers());
        Room room = roomRepository.findById(roomId).get();

        //assert
        assertThat(room.getRoomMembers().get(0).getMember().getMemberId()).isEqualTo("member1");
        assertThat(room.getRoomMembers().size()).isEqualTo(1);
        assertThat(room.getRoomMembers().get(0).getRoom().getTitle()).isEqualTo("title");
        // 새로 생성된 방은 entityManager의 범위 밖?
        assertThat(newRoom.getRoomMembers().size()).isEqualTo(0);
    }
}