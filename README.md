# XPress

## Description

XPress is a tool for finding XPath related bugs in XML processors using differential testing. 

## Supported XML Processors

- BaseX
- Saxon
- eXist-DB
- PostgreSQL
- libXML2
- Oracle

## Usage

```
mvn package
cd ./target
java -cp ./XPress.jar XPress.XPressRunner -test "Saxon;BaseX(user,password);" -log /home/x/log.txt -round 2
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
| eXist       | Config: /<br />Setup: 1. Start eXist  server 2. Create collection under /db with name test, and assign write permission for all users.<br />*There are known issues which leads to false alarms when using Saxon and eXist together, please avoid including both eXist and Saxon. To test eXist, run `./run_exist.sh 1` in sub-directory `eXist` (Reverse action:  `./run_exist.sh 0` ). |
| PostgreSQL  | Config: (user, password)<br />Setup: Start PostgreSQL server |
| libXML2     | Config: /<br />Setup: /                                      |
| Oracle      | Config: (user, password)<br />Setup: Start Oracle server     |

### Found Bugs

____

[Link to found bugs](./docs/Found_Bugs.md)

