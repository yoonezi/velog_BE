package com.study.velog.config;

import com.study.velog.domain.member.Member;
import com.study.velog.domain.member.MemberRepository;
import com.study.velog.domain.member.MemberStatus;
import com.study.velog.domain.post.*;
import com.study.velog.domain.postLike.PostLike;
import com.study.velog.domain.postLike.PostLikeRepository;
import com.study.velog.domain.tag.Tag;
import com.study.velog.domain.tag.TagRepository;
import com.study.velog.domain.type.PostCategory;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Profile("!test")
@RequiredArgsConstructor
@Component
public class DataInit {

    public static final String MASTER = "MASTER";
    public static final Long MASTER_ID = 1L;
    public static final String MASTER_EMAIL = "MASTER@MASTER.com";

    private final MemberRepository memberRepository;
    private final PostRepository postRepository;
    private final TagRepository tagRepository;
    private final PostLikeRepository postLikeRepository;
    private final RedisTemplate<String, String> redisTemplate;
    private final PasswordEncoder passwordEncoder;

    @PostConstruct
    public void init()
    {
        redisTemplate.execute(new RedisCallback<Object>() {
            public Object doInRedis(RedisConnection connection) {
                connection.flushAll();
                return null;
            }
        });

        Member member = memberRepository.save(
                Member.builder()
                        .memberStatus(MemberStatus.SERVICED)
                        .nickname(MASTER)
                        .email(MASTER_EMAIL)
                        .memberId(MASTER_ID)
                        .password(passwordEncoder.encode("password"))
                        .build()
        );

        Member member2 = memberRepository.save(
                Member.builder()
                        .memberStatus(MemberStatus.SERVICED)
                        .nickname(MASTER)
                        .email(MASTER_EMAIL + "DDDDD")
                        .memberId(2L)
                        .password(passwordEncoder.encode("password"))
                        .build()
        );

        Member member3 = memberRepository.save(
                Member.builder()
                        .memberStatus(MemberStatus.SERVICED)
                        .nickname(MASTER)
                        .email(MASTER_EMAIL + "DDsssDDD")
                        .memberId(3L)
                        .password(passwordEncoder.encode("password"))
                        .build()
        );


        Tag backend = tagRepository.save(
                Tag.builder().tagContent("Backend").build());
        Tag server = tagRepository.save(
                Tag.builder().tagContent("Server").build());

        Tag frontend = tagRepository.save(
                Tag.builder().tagContent("FrontEnd").build());
        Tag react = tagRepository.save(
                Tag.builder().tagContent("React").build());

        Set<PostTag> postTags1 = new HashSet<>();
        postTags1.add(PostTag.builder().tag(backend).build());
        postTags1.add(PostTag.builder().tag(server).build());

        Set<PostTag> postTags2 = new HashSet<>();
        postTags2.add(PostTag.builder().tag(frontend).build());
        postTags2.add(PostTag.builder().tag(react).build());

        Set<PostTag> postTags3 = new HashSet<>();
        postTags3.add(PostTag.builder().tag(frontend).build());
        postTags3.add(PostTag.builder().tag(react).build());

        Set<PostTag> postTags4 = new HashSet<>();
        postTags4.add(PostTag.builder().tag(frontend).build());
        postTags4.add(PostTag.builder().tag(react).build());

        Set<PostTag> postTags5 = new HashSet<>();
        postTags5.add(PostTag.builder().tag(backend).build());
        postTags5.add(PostTag.builder().tag(server).build());

        Set<PostImage> postImages1 = new HashSet<>(Arrays.asList(
                PostImage.builder()
                        .url("eb5cf268-e846-4e4f-b9be-585f6f4736ed-img6.jpeg")
                        .imageOrder(0)
                        .build(),
                PostImage.builder()
                        .url("eb5cf268-e846-4e4f-b9be-585f6f4736ed-img6.jpeg")
                        .imageOrder(1)
                        .build()
        ));

        Set<PostImage> postImages2 = new HashSet<>(Arrays.asList(
                PostImage.builder()
                        .url("ed59ab02-15fc-4ee4-8257-844cb91efbf5-img2.png")
                        .imageOrder(0)
                        .build(),
                PostImage.builder()
                        .url("ed59ab02-15fc-4ee4-8257-844cb91efbf5-img2.png")
                        .imageOrder(1)
                        .build()
        ));

        Set<PostImage> postImages3 = new HashSet<>(Arrays.asList(
                PostImage.builder()
                        .url("c68baa26-da4f-4651-9974-07e041e35e8d-img8.png")
                        .imageOrder(0)
                        .build(),
                PostImage.builder()
                        .url("c68baa26-da4f-4651-9974-07e041e35e8d-img8.png")
                        .imageOrder(1)
                        .build()
        ));

        Set<PostImage> postImages4 = new HashSet<>(Arrays.asList(
                PostImage.builder()
                        .url("db501527-c9d2-4fd9-9de7-712d42f7b266-img7.png")
                        .imageOrder(0)
                        .build(),
                PostImage.builder()
                        .url("db501527-c9d2-4fd9-9de7-712d42f7b266-img7.png")

                        .imageOrder(1)
                        .build()
        ));

        Set<PostImage> postImages5 = new HashSet<>(Arrays.asList(
                PostImage.builder()
                        .url("d728f4da-414b-47b8-b948-25c98bd56de9-img3.jpeg")
                        .imageOrder(0)
                        .build(),
                PostImage.builder()
                        .url("d728f4da-414b-47b8-b948-25c98bd56de9-img3.jpeg")
                        .imageOrder(1)
                        .build()
        ));

        Post post1 = Post.builder()
                .member(member)
                .content(" Config Store가 왜 필요해?\n" +
                        "대부분은 아래의 그림처럼 설정 정보는 자신의 서버에서 관리한다.\n" +
                        "서비스의 개수가 적다면 설정 정보 변경에 큰 힘이 들지 않는다.")
                .title("Spring Cloud Config가 뭐야?")
                .categoryType(PostCategory.BACKEND)
                .postStatus(PostStatus.SERVICED)
                .build();
        postImages1.forEach(post1::addPostImage);
        postTags1.forEach(post1::addPostTag);
//        postImages1.forEach(post1::addPostImage);

        postRepository.save(post1);


        Post post2 = Post.builder()
                .member(member)
                .content("자바 ORM 표준 JPA 프로그래밍 11장 웹 애플리케이션 제작을 학습하다가 궁금한 점이 생겼다. 관련 정보를 찾아보다 보니 유익한 점이 많은 것 같아 포스팅하게 되었다.")
                .title("JpaRepository.save 사용 시 주의사항")
                .categoryType(PostCategory.GAME)
                .postStatus(PostStatus.SERVICED)
                .postImageList(postImages2)
//                .postTags(postTags2)
                .build();
        postImages2.forEach(post2::addPostImage);
        postTags2.forEach(post2::addPostTag);
//        postImages2.forEach(post2::addPostImage);

        postRepository.save(post2);

        Post post3 = Post.builder()
                .member(member)
                .content("온라인 결제 연동은 어렵고 복잡하다는 인식이 있어요. 하지만 토스페이먼츠와 함께라면 PG 심사부터 연동까지 간편하게 완료할 수 있어요. 오늘은 토스페이먼츠 PG 계약을 신청하는 방법과 결제를 연동하는 방법을 간략히 알아볼게요.")
                .title("토스페이먼츠 결제 연동하기")
                .categoryType(PostCategory.DEVOPS)
                .postStatus(PostStatus.SERVICED)
                .build();
        postImages3.forEach(post3::addPostImage);
        postTags3.forEach(post3::addPostTag);

        postRepository.save(post3);

        Post post4 = Post.builder()
                .member(member)
                .content("나는 리눅스를 3개월 전 (23.12월) 처음 접해보았다. 당시 주제는 라즈베리파이를 필수로 사용해서 시스템 하나를 개발하는 것 이였다.\n" +
                        "\n" +
                        "처음 사용해 본 라즈베리파이에는 리눅스 환경이 적용 되어 있었고, 터미널에서 명령어를 입력해 파일 구조 및 코드 넣기 등 모든 것을 해야 했었다.")
                .title("리눅스를 외자 명령어를 외자")
                .categoryType(PostCategory.FRONTEND)
                .postStatus(PostStatus.SERVICED)
                .build();
        postImages4.forEach(post4::addPostImage);
        postTags4.forEach(post4::addPostTag);

        postRepository.save(post4);

        Post post5 = Post.builder()
                .member(member)
                .content("개발자는 노코드 툴을 왜 사용해야 될까요? 버블은 풀스택 기능, 데이터 관리 모두 노코드로 할 수 있기 때문에 간단한 서비스를 시작하는 단계에서 사용하면 매우 유용해요. 오늘은 버블에서 코드 없이 결제 연동하는 방법을 알아볼게요.")
                .title("노코드로 결제 연동하기")
                .categoryType(PostCategory.AI)
                .postStatus(PostStatus.SERVICED)
                .build();

        post5.setViewCount(5);
        post4.setViewCount(2);
        post2.setViewCount(1);
        postImages5.forEach(post5::addPostImage);
        postTags5.forEach(post5::addPostTag);

        postRepository.save(post5);

        PostLike postLike1 = PostLike.builder().post(post3).member(member3).build();
        PostLike postLike2 = PostLike.builder().post(post4).member(member2).build();
        postLikeRepository.saveAllAndFlush(List.of(postLike1, postLike2));
    }
}
