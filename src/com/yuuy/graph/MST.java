package com.yuuy.graph;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

interface MST {
    Iterable<Edge> edges();

    double weight();

}
