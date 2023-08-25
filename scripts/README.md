# README

## Description

Scripts in this directory could be used to reproduce experimental results in the evaluation section. 

### Before Testing

```bash
docker build -t xpress_eval .
```

### Evaluation Test 1

___

```bash
./evaluation_get.sh $dir_to_store_results$ $times_to_repeat_experiment$ $time_to_run_experiment_in_sec$
```

```bash
export BASEX_HOME=$path_to_basex_repo$
./evaluation_result.sh $dir_to_store_results$ $times_to_repeat_experiment$ $whether to cover previous results(true or false without quotes)$
```

### Evaluation Test 2

____

```bash
./evaluation_get2.sh $dir_to_store_results$ $times_to_repeat_experiment$ $time_to_run_experiment_in_sec$
python3 evaluation_result2.py
```





