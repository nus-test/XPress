## README

```
├── experiment
│   ├── 1 (the first run)
│   │   ├── diagnosis_{config}.txt
│   │   ├── evaluation_{config}.txt
│   │   ├── report_{config}.txt
│   ├── 2 (the second run)
│   ├── ...
├── BaseX Historical Bug Analysis.xlsx
└── README.md
```

##### Config: 

​	s_r: targeted w Rect

​	s_nr: Targeted w/o Rect

​	p_r: Untargeted w Rect

​	p_nr: Untargeted w/o Rect

##### diagnosis_{config}.txt: 

​	Count of total test cases, non-empty test cases and invalid test cases under {config} in 24 hours.

##### evaluation_{config}.txt:

​	Count of total test cases and discrepancy detected test cases under {config} in 24 hours.

##### report_{config}.txt:

​	Recording of all discrepancy detected test cases under {config} in 24 hours. `----` marks testing for 1 hour. 

