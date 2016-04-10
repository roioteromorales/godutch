package com.roisoftstudio.godutch.integration.info.path;

import com.github.kevinsawicki.http.HttpRequest;
import com.google.gson.Gson;
import com.roisoftstudio.godutch.info.model.InfoModel;
import org.junit.Test;

import static com.roisoftstudio.godutch.config.ConfigurationConstants.CONTAINER_URL;
import static org.assertj.core.api.Assertions.assertThat;

public class InfoPathTest {

    @Test
    public void infoPath_shouldReturnInfoModel() throws Exception {
        String response = HttpRequest.get(CONTAINER_URL).body();
        InfoModel infoModel = new Gson().fromJson(response, InfoModel.class);
        assertThat(infoModel.getMsName()).isEqualTo("GoDutch MicroService");
    }

}
