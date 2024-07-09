package io.github.mouday.utils;

import java.io.*;

/**
 * 执行系统命令
 */
public final class ShellUtil {
    private ShellUtil() {
    }

    /**
     * 操作系统名称
     *
     * @return Mac OS X
     */
    public static String getOsName() {
        return System.getProperty("os.name");
    }

    /**
     * 判断是否为windows操作系统
     *
     * @return
     */
    public static Boolean isWindows() {
        return ShellUtil.getOsName().toLowerCase().contains("win");
    }

    /**
     * 根据系统返回对应平台执行命令
     * @param command
     * @return
     */
    public static ProcessBuilder getProcessBuilder(String command) {
        if (ShellUtil.isWindows()) {
            return new ProcessBuilder("cmd", "/c", command);
        } else {
            return new ProcessBuilder("bash", "-c", command);
        }
    }

    /**
     * 执行系统命令
     * @param command
     * @return
     */
    public static String executeCommand(String command) {
        try {
            ProcessBuilder pb = ShellUtil.getProcessBuilder(command);
            pb.redirectErrorStream(true);

            Process process = pb.start();
            process.waitFor();

            InputStream inputStream = process.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            StringBuilder result = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                result.append(line);

                // 换行符
                result.append(System.lineSeparator());
            }

            process.waitFor();
            inputStream.close();
            reader.close();

            process.destroy();

            return result.toString();
        } catch (InterruptedException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        // System.out.println(ShellUtil.executeCommand("echo 'hello'"));
        // hello

        System.out.println(ShellUtil.executeCommand("python math.py 1 2"));
        // 3
    }
}
