package application.usecases;

public class FilterUseCaseResponse {
    private final String filterRequest;

    public FilterUseCaseResponse(String result) {
        this.filterRequest = result;
    }

    public String getResult() {
        return filterRequest;
    }
}
