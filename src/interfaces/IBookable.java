package interfaces;

public interface IBookable {
    String getStatus();
    void cancel();
    void complete();
    boolean isConfirmed();
}