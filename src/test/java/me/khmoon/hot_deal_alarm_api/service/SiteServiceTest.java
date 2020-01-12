package me.khmoon.hot_deal_alarm_api.service;

import me.khmoon.hot_deal_alarm_api.domain.site.Site;
import me.khmoon.hot_deal_alarm_api.domain.site.SiteName;
import me.khmoon.hot_deal_alarm_api.propertiy.ApplicationProperties;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ActiveProfiles("test")
@SpringBootTest
@Transactional
class SiteServiceTest {
  @Autowired
  private SiteService siteService;
  @Autowired
  private ApplicationProperties applicationProperties;
  @Autowired
  private EntityManager em;
  private String siteListUrl;
  private String siteViewUrl;

  @PostConstruct
  public void init() {
    siteListUrl = applicationProperties.getPpomppu().getUrl().getList();
    siteViewUrl = applicationProperties.getPpomppu().getUrl().getView();
  }


  private SiteName siteName = SiteName.PPOMPPU;


  @Test
  @DisplayName("추가하기")
  void add() {
    Site site = Site.builder().siteName(siteName).siteListUrl(siteListUrl).siteViewUrl(siteViewUrl).build();
    siteService.add(site);
    Site site1 = siteService.findOne(site.getId());
    assertEquals(site1.getSiteName(), siteName);
    assertEquals(site1.getSiteListUrl(), siteListUrl);
    assertEquals(site1.getSiteViewUrl(), siteViewUrl);
  }

  @Test
  @DisplayName("유니큐 조건")
  void uniqueViolation() {
    Site site = Site.builder().siteName(siteName).siteListUrl(siteListUrl).siteViewUrl(siteViewUrl).build();
    siteService.add(site);
    Site site2 = Site.builder().siteName(siteName).siteListUrl(siteListUrl).siteViewUrl(siteViewUrl).build();
    siteService.add(site2);
    assertThrows(PersistenceException.class, () -> em.flush());
    //DataIntegrityViolationException 기존에 이 exception은 save할 때 나오던게 generate value를 기본으로 변경해보니, save가 바로 안 나가서 exception처리를 이렇게 변경하였다.
  }

  @Test
  @DisplayName("url format test")
  void ppomppuUrlStringFormat() {
    String ppomppuListUrl = String.format(siteListUrl, "ppomppu", 1);
    String ppomppuViewUrl = String.format(siteViewUrl, "ppomppu", 339349);
    assertEquals(ppomppuListUrl, "https://m.ppomppu.co.kr/new/bbs_list.php?id=ppomppu&page=1");
    assertEquals(ppomppuViewUrl, "https://m.ppomppu.co.kr/new/bbs_view.php?id=ppomppu&no=339349");
  }

  @Test
  void checkProperty() {
    assertEquals(siteListUrl, applicationProperties.getPpomppu().getUrl().getList());
    assertEquals(siteViewUrl, applicationProperties.getPpomppu().getUrl().getView());
  }
}