package me.khmoon.hot_deal_alarm_api.propertiy;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("my-values")
@Getter
@Setter
public class ApplicationProperties {
  private String userAgent;
  private Ppomppu ppomppu;
  private Dealbada dealbada;
  private Clien clien;
  private Coolenjoy coolenjoy;

  @Getter
  @Setter
  public static class Ppomppu {
    private Url url;
    private Param param;

    @Getter
    @Setter
    public static class Url {
      private String list;
      private String view;
    }

    @Getter
    @Setter
    public static class Param {
      private String domestic;
      private String overseas;
    }
  }


  @Getter
  @Setter
  public static class Dealbada {
    private Url url;
    private Param param;

    @Getter
    @Setter
    public static class Url {
      private String list;
      private String view;
    }

    @Getter
    @Setter
    public static class Param {
      private String domestic;
      private String overseas;
    }
  }

  @Getter
  @Setter
  public static class Clien {
    private Url url;
    private Param param;

    @Getter
    @Setter
    public static class Url {
      private String list;
      private String view;
    }

    @Getter
    @Setter
    public static class Param {
      private String thrifty;
    }
  }

  @Getter
  @Setter
  public static class Coolenjoy {
    private Url url;
    private Param param;

    @Getter
    @Setter
    public static class Url {
      private String list;
      private String view;
    }

    @Getter
    @Setter
    public static class Param {
      private String thrifty;
    }
  }
}
