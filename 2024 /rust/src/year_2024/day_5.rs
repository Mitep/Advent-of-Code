use std::{cmp::Ordering, collections::HashMap, fs};

pub fn solve_part_1() -> i32 {
    let input =
        fs::read_to_string("src/year_2024/inputs/day_5.txt").expect("Oops, could not open file.");

    let (rules, validations) = input.split_once("\n\n").unwrap();

    let map = map_rules(rules);

    validations
        .lines()
        .filter_map(|line| {
            let to_check = line
                .split(",")
                .map(|num| num.parse::<i32>().unwrap())
                .collect::<Vec<i32>>();

            if to_check
                .iter()
                .enumerate()
                .any(|(i, num)| match map.get(num) {
                    Some(v) => v.iter().any(|b| to_check[0..=i].contains(b)),
                    None => false,
                })
            {
                return None;
            }

            Some(to_check[to_check.len() / 2])
        })
        .sum::<i32>()
}

pub fn solve_part_2() -> i32 {
    let input =
        fs::read_to_string("../inputs/05.in").expect("Oops, could not open file.");

    let (rules, validations) = input.split_once("\n\n").unwrap();

    let map = map_rules(rules);

    validations
        .lines()
        .filter_map(|line| {
            let mut to_check = line
                .split(",")
                .map(|num| num.parse::<i32>().unwrap())
                .collect::<Vec<i32>>();

            if !to_check
                .iter()
                .enumerate()
                .any(|(i, num)| match map.get(num) {
                    Some(v) => v.iter().any(|b| to_check[0..=i].contains(b)),
                    None => false,
                })
            {
                return None;
            }

            to_check.sort_by(|a, b| match map.get(a) {
                Some(v) => {
                    if v.contains(b) {
                        Ordering::Less
                    } else {
                        Ordering::Greater
                    }
                }
                None => Ordering::Equal,
            });

            Some(to_check[to_check.len() / 2])
        })
        .sum::<i32>()
}

fn map_rules(rules: &str) -> HashMap<i32, Vec<i32>> {
    rules
        .lines()
        .fold(HashMap::new(), |mut map: HashMap<i32, Vec<i32>>, l| {
            let nums = l.split_once("|").unwrap();
            let (p1, p2) = (
                nums.0.parse::<i32>().unwrap(),
                nums.1.parse::<i32>().unwrap(),
            );

            map.entry(p1)
                .and_modify(|v| v.push(p2))
                .or_insert_with(|| vec![p2]);

            map
        })
}