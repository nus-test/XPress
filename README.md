# XPress

## Purpose

XPress is a tool for finding XPath related bugs in XML processors using differential testing. 

## Supported XML Processors

- BaseX
- Saxon
- eXist-DB
- PostgreSQL
- libXML2
- Oracle

## Setup
Please install and use JDK-17 as Java environment. 

## Usage

First, set up all systems under test according to the instructions in Section [SystemSettings](#system-setups).
Then, run the following commands in the root directory of XPress:

```
mvn package -Dmaven.test.skip
# Command with example options and configurations
java -cp ./target/XPress.jar XPress.XPressRunner -test "Saxon;BaseX($user,$password);" -log /home/x/log.txt -round 2
```

Full runnable example testing BaseX and Saxon:

```
# Setup for BaseX: Start BaseX server (github commit d1bb20b)
$ cd ..
$ git clone https://github.com/BaseXdb/basex.git
$ cd ./XPress
$ ./scripts/basex_version_setup.sh ../basex d1bb20b

# Run XPress
$ mvn package -Dmaven.test.skip
$ java -cp ./target/XPress.jar XPress.XPressRunner -test "Saxon;BaseX(admin, pass);" -log ./log.txt -round 2

# Shutdown BaseX server
$ server_PID=`cat server_PID.txt` && kill -9 $server_PID

# Example Output

# Show generated test cases, and if suspected bugs were detected the test cases are saved to log.txt
# Generated test cases example:
# <T id="1">true</T> 
# Generated XPath: 0 //T[text() = true()]
# Generated XPath: 1 //*
# Generated XPath: ...

# Suspected Bugs
# Bug record example: (In log.txt, includes id, test case that results in discrepancy, and results of all systems under test)
# id:1
# originalxml:<T id="1">true</T>
# originalxpath://*
# executionresults:
# System1:
# successfulexecution:1
# System2:
# successfulexecution:2
```

### Options

___

| Option name | Description                                                  |
| ----------- | ------------------------------------------------------------ |
| test        | Semicolon separated names of systems to use in differential testing. Each system name could be followed by configuration arguments separated by commas and closed by parentheses. |
| log         | File location to log bug reports.                            |
| round       | Total testing rounds. One XML document is generated for each round of testing. |
| xpath       | XPath expressions to test for each round.                    |
| section     | Maximum number of sections to generate for each XPath expression. |
| standard    | Standard of XPath under test: 1 or 3.                        |
| default     | Name of system to use for query construction; Could be followed by configuration arguments, overwrites config arguments in option test if defined twice. |

### System Setups

___

| System name | Command line configuration & Setup                           |
| ----------- | ------------------------------------------------------------ |
| BaseX       | Config: (user, password)<br />Setup: Start BaseX server      |
| Saxon       | Config: /<br />Setup: /                                      |
| eXist       | Config: /<br />Setup: 1. Start eXist  server 2. Create collection under /db with name test, and assign write permission for all users.<br />*There are known issues which leads to false alarms when using Saxon and eXist together, please avoid including both eXist and Saxon. To test eXist, run `./run_exist.sh 1` in sub-directory `eXist` before building XPress (Reverse action:  `./run_exist.sh 0` ). |
| PostgreSQL  | Config: (user, password)<br />Setup: Start PostgreSQL server |
| libXML2     | Config: /<br />Setup: /                                      |
| Oracle      | Config: (user, password)<br />Setup: Start Oracle server     |

### Adding Support for Additional Systems

____

To add support for an additional system, please instantiate a specific `DatabaseExecutor` which extends abstract class `XPress.DatabaseExecutor.DatabaseExecutor` and annotate it with `@Executor`. Please refer to comments in `XPress.DatabaseExecutor.DatabaseExecutor` and also examples of implemented executors such as `BaseXExecutor` and `SaxonExecutor` for more details.

### Found Bugs

____

XPress had found bugs in several XML processors. 

[Link to found bugs](./docs/Found&#32;Bugs.md)

To find the listed bugs, run XPress to perform differential testing for the buggy system and a newest version of other XML processor. 

In the results file, test cases to trigger the bug is recorded. 

However, to produce the exact same results may be difficult due to several reasons: 

1. Test cases are generated randomly, therefore some time might be required to produce specific test case to trigger a bug.
2. The test cases are complex and needs manual reduce to show minimized bug pattern.
3. Faults occur in different stages of system development, therefore testing on a single version of a system could not find all reported bugs. 
