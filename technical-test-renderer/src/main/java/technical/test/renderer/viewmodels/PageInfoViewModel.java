package technical.test.renderer.viewmodels;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PageInfoViewModel {
    private int currentPage;
    private int totalPages;
    private long totalElements;
    private boolean hasNext;
    private boolean hasPrevious;

    public int getNextPage() {
        return hasNext ? currentPage + 1 : currentPage;
    }

    public int getPreviousPage() {
        return hasPrevious ? currentPage - 1 : 0;
    }
}