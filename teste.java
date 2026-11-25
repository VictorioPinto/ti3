import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class AzureClient {
    private static final String ENDPOINT = "https://openai-ti3.openai.azure.com/openai/v1/chat/completions";
    private static final String API_KEY = System.getenv("AZURE_OPENAI_KEY");

    public static String enviarPrompt(String prompt) throws Exception {
        String jsonBody = """
            {
              "model": "gpt-4o",
              "temperature": 0,
              "messages": [
                {"role": "system", "content": "Você é um desenvolvedor Java. Retorne apenas o código solicitado."},
                {"role": "user", "content": "%s"}
              ]
            }
            """.formatted(prompt.replace("\"", "\\\"").replace("\n", "\\n"));

        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(ENDPOINT))
            .header("Content-Type", "application/json")
            .header("Authorization", "Bearer " + API_KEY)
            .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
            .build();

        HttpClient client = HttpClient.newHttpClient();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return response.body();
    }

    public static void main(String[] args) throws Exception {
        System.out.println(enviarPrompt("Crie uma classe Produto em Java com id, nome e preco."));
    }
}