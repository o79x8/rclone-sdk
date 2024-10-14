public class ProgressDTO {
    private String transferredData;

    private String totalData;

    private String dataPercentage;

    private String transferSpeed;

    private String estimatedTime;

    private int transferredFiles;

    private int totalFiles;

    private String filePercentage;

    public String getTransferredData() {
        return transferredData;
    }

    public void setTransferredData(String transferredData) {
        this.transferredData = transferredData;
    }

    public String getTotalData() {
        return totalData;
    }

    public void setTotalData(String totalData) {
        this.totalData = totalData;
    }

    public String getDataPercentage() {
        return dataPercentage;
    }

    public void setDataPercentage(String dataPercentage) {
        this.dataPercentage = dataPercentage;
    }

    public String getTransferSpeed() {
        return transferSpeed;
    }

    public void setTransferSpeed(String transferSpeed) {
        this.transferSpeed = transferSpeed;
    }

    public String getEstimatedTime() {
        return estimatedTime;
    }

    public void setEstimatedTime(String estimatedTime) {
        this.estimatedTime = estimatedTime;
    }

    public int getTransferredFiles() {
        return transferredFiles;
    }

    public void setTransferredFiles(int transferredFiles) {
        this.transferredFiles = transferredFiles;
    }

    public int getTotalFiles() {
        return totalFiles;
    }

    public void setTotalFiles(int totalFiles) {
        this.totalFiles = totalFiles;
    }

    public String getFilePercentage() {
        return filePercentage;
    }

    public void setFilePercentage(String filePercentage) {
        this.filePercentage = filePercentage;
    }

    @Override
    public String toString() {
        return "ProgressDTO{" +
                "transferredData='" + transferredData + '\'' +
                ", totalData='" + totalData + '\'' +
                ", dataPercentage='" + dataPercentage + '\'' +
                ", transferSpeed='" + transferSpeed + '\'' +
                ", estimatedTime='" + estimatedTime + '\'' +
                ", transferredFiles=" + transferredFiles +
                ", totalFiles=" + totalFiles +
                ", filePercentage='" + filePercentage + '\'' +
                '}';
    }
}
