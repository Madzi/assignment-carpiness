package com.carpiness.job;

import com.carpiness.job.config.AppState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static java.util.Arrays.asList;

public class App {

    private static final Logger LOG = LoggerFactory.getLogger(App.class);

    private static final String PARAM_HELP         = "--help";
    private static final String PARAM_IGNORE_ERROR = "--ignore-error";

    public static void main(String... args) {
        LOG.info("Start");

        Set<String> params = new HashSet<>(Arrays.asList(args)); // To prevent duplicate file names

        if (params.contains(PARAM_HELP)) {
            params.remove(PARAM_HELP);
            showHelp();
        }

        if (params.contains(PARAM_IGNORE_ERROR)) {
            params.remove(PARAM_IGNORE_ERROR);
            ignoreFailFast();
        }

        if (params.isEmpty()) {
            consoleWork();
        } else {
            processFiles(params);
        }

        LOG.info("Finish");
    }

    private static void showHelp() {
        asList(
                "USAGE: <program> [option] [file-name-list]",
                "",
                "    option",
                "        Only two option supported:",
                "            --help             : This screen will be displayed.",
                "            --ignore-fail-fast : All exceptions will be skipped. It's may be useful for batch processing.",
                "",
                "    file-name-list",
                "        This is a list of file names without extensions.",
                "        All file names from this list represents pair of files: \"<file-name>.in\" and \"<file-name>.out\".",
                "",
                "If file-name-list not defined and option is not \"--help\" then program will be expect input from console."
        ).forEach(System.out::println);
    }

    private static void ignoreFailFast() {
        AppState.INSTANCE.switchOffFailFast();
    }

    private static void consoleWork() {}

    private static void processFiles(Set<String> fileNames) {}

//    private static void processFile(String fileName) {
//        try (Stream<String> stream = Files.lines(Paths.get(fileName))) {
//        } catch (IOException ex) {
//            if (AppState.INSTANCE.isFailFast()) {
//                LOG.error("Unable read {}", fileName, ex);
//                System.exit(-1);
//            } else {
//                LOG.warn("Unable read {}", fileName, ex);
//            }
//        }
//    }

}
