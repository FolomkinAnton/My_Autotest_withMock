import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import com.github.tomakehurst.wiremock.extension.responsetemplating.ResponseTemplateTransformer;
import com.github.tomakehurst.wiremock.junit.WireMockRule;
import org.junit.Rule;
import static com.github.tomakehurst.wiremock.client.WireMock.*;

class Mock {

    //включаем Response Templating для получения данных из запроса (request.body) (request.query)
    @Rule
    public WireMockRule wireMockRule = new WireMockRule();

    private static WireMockConfiguration configure() {
        return WireMockConfiguration.wireMockConfig()
                .extensions(new ResponseTemplateTransformer(true));
    }

    public static void setUp(){ //в данном методе настраивается мок сервер


        //настройка запроса post
        stubFor(post(urlPathMatching("/mock/create_employee"))
                .willReturn(aResponse()
                        .withStatus(201)
                        .withHeader("Content-Type","application/json")
                        .withBody("{\"fio\": \"{{jsonPath request.body '$.fio'}}\" , \"position\": \"{{jsonPath request.body '$.position'}}\", \"number\": \"{{jsonPath request.body '$.number'}}\", \"id\": \"{{randomValue type='UUID'}}\" }" )));

        //настройка запроса get
        stubFor(get(urlPathMatching("/mock/get_employee"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type","application/json")
                        .withBody("{\"id\": \"227\" , \"fio\": \"Иванов Иван Иванович\", \"position\": \"Cпециалист по тестированию\", \"number\": \"2\" }" )));
        //настройка запроса delete
        stubFor(delete(urlPathMatching("/mock/delete_employee"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type","application/json")
                        .withBody("{\"id\": \"{{capitalize request.query.id}}\" , \"status\": \"DELETED\"}" )));
    }

    //запуск мок сервера, настройка порта
    public static void main(String[] args) {
        WireMockServer wireMockServer = new WireMockServer(configure().port(8008));
        wireMockServer.start();
        WireMock.configureFor("localhost", 8008);
        setUp();
    }

}
