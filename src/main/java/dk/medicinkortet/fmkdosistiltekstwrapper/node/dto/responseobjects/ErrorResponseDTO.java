package dk.medicinkortet.fmkdosistiltekstwrapper.node.dto.responseobjects;

public class ErrorResponseDTO {
    private String msg;
    private boolean success;

    public ErrorResponseDTO() {
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
