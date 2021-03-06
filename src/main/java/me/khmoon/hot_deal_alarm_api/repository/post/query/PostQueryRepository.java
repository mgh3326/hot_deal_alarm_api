package me.khmoon.hot_deal_alarm_api.repository.post.query;

import lombok.RequiredArgsConstructor;
import me.khmoon.hot_deal_alarm_api.domain.post.Post;
import me.khmoon.hot_deal_alarm_api.repository.PostRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class PostQueryRepository {
  private final PostRepository postRepository;

  public PostRes findPostDtoAllByBoardId(Long boardId, int page) {
    PageRequest pageRequest = PageRequest.of(page, 50, Sort.by(Sort.Direction.DESC,
            "postOriginId"));
    Slice<Post> postByBoardId = postRepository.findPostsByBoardId(boardId, pageRequest);
    return new PostRes(postByBoardId);
  }

  public PostAllRes findPostDtoAll(int page) {
    PageRequest pageRequest = PageRequest.of(page, 50, Sort.by(Sort.Direction.DESC,
            "createdDate"));
    Slice<Post> posts = postRepository.findPosts(pageRequest);
    PostAllRes postAllRes = new PostAllRes(posts);
    return postAllRes;
  }
}
