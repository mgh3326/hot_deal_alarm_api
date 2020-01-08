package me.khmoon.hot_deal_alarm_api.programmableScheduler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.khmoon.hot_deal_alarm_api.domain.page.Page;
import me.khmoon.hot_deal_alarm_api.domain.site.Site;
import me.khmoon.hot_deal_alarm_api.service.PageService;
import me.khmoon.hot_deal_alarm_api.service.SiteService;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.PeriodicTrigger;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@Component
@Slf4j
@RequiredArgsConstructor
public class ProgrammableScheduler {
  private final SiteService siteService;
  private final PageService pageService;
  ThreadPoolTaskScheduler[] schedulerArray;

  public void stopScheduler() { // TODO 데이터 변동에 따라서 이게 재 실행 되야할 것 같다. (Annotaion으로 구성하면 좋을것 같다.)
    for (ThreadPoolTaskScheduler scheduler : schedulerArray) {
      scheduler.shutdown();
    }
  }

  public void startScheduler() {
    List<Site> sites = siteService.findAll();
    int size = sites.size();
    System.out.println("size = " + size);
    schedulerArray = new ThreadPoolTaskScheduler[size];
    for (int i = 0; i < size; i++) {
      schedulerArray[i] = new ThreadPoolTaskScheduler();
      schedulerArray[i].initialize();
      // 스케쥴러가 시작되는 부분
      schedulerArray[i].schedule(getRunnable(sites.get(i).getId()), getTrigger(sites.get(i).getId()));
    }

  }

  private Runnable getRunnable(Long siteId) {
    return () -> {
      // do something
      log.info(String.valueOf(LocalDateTime.now()));
      log.info(String.format("SiteID : %d", siteId));
      try {
        Random random = new Random();
        Thread.sleep(random.nextInt(5000));
      } catch (InterruptedException e) {
        e.printStackTrace();
      }

    };
  }

  private Trigger getTrigger(Long siteId) {
    // 작업 주기 설정
    List<Page> pages = pageService.findAllBySiteId(siteId); // TODO 이런건 count로 하면 좋을 것 같다.
    System.out.println("pages.size() = " + pages.size());
    return new PeriodicTrigger(1, TimeUnit.SECONDS);
//    return new PeriodicTrigger(60 / pages.size(), TimeUnit.SECONDS);
  }
}
