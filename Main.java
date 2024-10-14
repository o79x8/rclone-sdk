import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");
        syncDirectories();
//        String[] exampleOutput = {
//                "Transferred:          16.725 MiB / 12.160 GiB, 0%, 1.184 MiB/s, ETA 2h54m59s",
//                "Transferred:          0 B / 0 B, -, 0 B/s, ETA -, Transferred:            0 / 2, 0%",
//                "Transferred:               97 / 10104, 1%"
//        };

//        ProgressDTO progress = parseRcloneOutput(exampleOutput);
//        System.out.println("Progress: " + progress);
    }

    public static void syncDirectories() {
        ProcessBuilder processBuilder = new ProcessBuilder();
        processBuilder.command("rclone", "copy", "TencentCOS:innoc-test-1307282920/st-qa/3", "minio:innoe/st-qa/3", "--progress", "-v");

        try {
            Process process = processBuilder.start();
            String[] outputLines = new String[2];

            // 创建一个线程来读取rclone的输出流
            Thread outputMonitorThread = new Thread(() -> {
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        // 打印同步进度信息
                        if (line.startsWith("Transferred:")) {
                            line = line.replace("Transferred:", "").trim();
                            if (line.contains("ETA")) {
                                outputLines[0] = line;
                            } else {
                                outputLines[1] = line;
                            }

                            if (outputLines[0] != null && outputLines[1] != null) {
                                ProgressDTO progressDTO = parseRcloneOutput(outputLines);
                                System.out.println(progressDTO);
                                outputLines.clone();
                            }
                        }

                        // 这里可以添加解析逻辑，根据需要提取同步进度信息
                        // 例如：提取传输速率、文件数量等信息
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });

            outputMonitorThread.start();

            // 阻塞，直到rclone进程完成
            int exitCode = process.waitFor();
            outputMonitorThread.join();

            if (exitCode == 0) {
                System.out.println("Sync completed successfully.");
            } else {
                System.err.println("Sync failed.");
            }

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static ProgressDTO parseRcloneOutput(String[] outputLines) {
        if (outputLines == null || outputLines.length < 2) {
            throw new IllegalArgumentException("Invalid Rclone output.");
        }

        ProgressDTO progressDTO = new ProgressDTO();

        String[] dataParts = outputLines[0].split(",");
        if (dataParts.length == 4) {
            String[] dataContents = dataParts[0].split("/");
            progressDTO.setTransferredData(dataContents[0].trim());
            progressDTO.setTotalData(dataContents[1].trim());
            progressDTO.setDataPercentage(dataParts[1].trim());
            progressDTO.setTransferSpeed(dataParts[2].trim());
            progressDTO.setEstimatedTime(dataParts[3].trim());
        }

        String[] fileParts = outputLines[1].split(",");
        if (fileParts.length == 2) {
            String[] fileNumbers = fileParts[0].split("/");
            progressDTO.setTransferredFiles(Integer.parseInt(fileNumbers[0].trim()));
            progressDTO.setTotalFiles(Integer.parseInt(fileNumbers[1].trim()));
            progressDTO.setFilePercentage(fileParts[1].trim());
        }

        return progressDTO;
    }
}