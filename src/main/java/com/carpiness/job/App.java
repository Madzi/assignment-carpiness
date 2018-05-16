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

    private ApplicationContext context = ApplicationContext.INSTANCE;
    private BeanConfig config = BeanConfig.INSTANCE;

    public static void main(String... args) {
        LOG.info("Start");
        App app = new App();
        app.processParams(args);
        LOG.info("Finish");
    }

    public void processParams(String... args) {
        Set<String> params = new HashSet<>(asList(args)); // To prevent duplicate file names

        if (params.remove(PARAM_HELP)) {
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
        if (params.remove(PARAM_IGNORE_ERROR)) {
            context.switchOffFailFast();
        }
        if (params.remove(PARAM_IGNORE_CASE)) {
            context.switchOffCaseSensitivity();
        }

        if (params.isEmpty()) {
            System.out.println("Please input data. To finish input empty line. (Use '--help' for more options)\n");
            ConsoleDriver consoleDriver = config.getConsoleDriver();
            StringParser jobLoader = config.getStringParser();
            ChargeCalculator calculator = config.getChargeCalculator();
            Iterable<String> lines = consoleDriver.load();
            Job job = jobLoader.load(lines);
            List<String> result = calculator.calc(job);
            consoleDriver.save(result);
        } else {
            FileProcessor fileProcessor = config.getFileProcessor();
            params.forEach(fileProcessor::process);
        }
    }

}
