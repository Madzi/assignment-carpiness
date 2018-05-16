package com.carpiness.job;

import com.carpiness.job.config.ApplicationContext;
import com.carpiness.job.config.BeanConfig;
import com.carpiness.job.domain.Job;
import com.carpiness.job.input.FileProcessor;
import com.carpiness.job.input.console.ConsoleDriver;
import com.carpiness.job.service.ChargeCalculator;
import com.carpiness.job.service.StringParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static java.util.Arrays.asList;

public class App {

    private static final Logger LOG = LoggerFactory.getLogger(App.class);

    private static final String PARAM_HELP         = "--help";
    private static final String PARAM_IGNORE_ERROR = "--ignore-error";
    private static final String PARAM_IGNORE_CASE  = "--ignore-case";

    public static void main(String... args) {
        LOG.info("Start");

        Set<String> params = new HashSet<>(asList(args)); // To prevent duplicate file names

        if (params.contains(PARAM_HELP)) {
            params.remove(PARAM_HELP);
            showHelp();
        }

        if (params.contains(PARAM_IGNORE_ERROR)) {
            params.remove(PARAM_IGNORE_ERROR);
            ignoreFailFast();
        }

        if (params.contains(PARAM_IGNORE_CASE)) {
            params.remove(PARAM_IGNORE_CASE);
            ignoreCase();
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
                "            --help         : This screen will be displayed.",
                "            --ignore-error : All exceptions will be skipped. It's may be useful for batch processing.",
                "            --ignore-case  : The 'extra-margin' and 'exempt' keywords will be accepted in any case.",
                "",
                "    file-name-list",
                "        This is a list of file names without extensions.",
                "        All file names from this list represents pair of files: \"<file-name>.in\" and \"<file-name>.out\".",
                "",
                "If file-name-list not defined and option is not \"--help\" then program will be expect input from console."
        ).forEach(System.out::println);
    }

    private static void ignoreFailFast() {
        ApplicationContext.INSTANCE.switchOffFailFast();
    }

    private static void ignoreCase() {
        ApplicationContext.INSTANCE.switchOffCaseSensitivity();
    }

    private static void consoleWork() {
        BeanConfig config = BeanConfig.INSTANCE;
        System.out.println("Please input data. To finish input empty line.\n");
        ConsoleDriver consoleDriver = config.getConsoleDriver();
        StringParser jobLoader = config.getStringParser();
        ChargeCalculator calculator = config.getChargeCalculator();
        Iterable<String> lines = consoleDriver.load();
        Job job = jobLoader.load(lines);
        List<String> result = calculator.calc(job);
        consoleDriver.save(result);
    }

    private static void processFiles(Set<String> fileNames) {
        FileProcessor fileProcessor = BeanConfig.INSTANCE.getFileProcessor();
        fileNames.forEach(fileProcessor::process);
    }

}
