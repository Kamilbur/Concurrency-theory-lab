## Python program for computing many representations of traces from trace theory.
Example:
alphabet: A = {a, b, c, d, e, f}

independence relation: I = {(a, d), (d, a), (b, f), (f, b), (a, c), (c, a)}

word: w = "afadbdfba"

dependency relation: D = {(e, b), (a, f), (a, e), (e, a), (c, f), (e, c), (d, b), (f, a), (a, b), (d, d), (b, a), (b, d), (b, c), (f, f), (f, c), (f, e), (a, a), (d, e), (b, e), (c, c), (e, e), (e, d), (f, d), (e, f), (c, d), (b, b), (d, c), (c, e), (d, f), (c, b)}

trace as an equivalence class: \[w\] = {'afdabdfba', 'afadbdbfa', 'afdabdbfa', 'afadbdfba'}

Foata normal form:          (a)(f)(ad)(b)(d)(bf)(a)

Dependency graph:
<p align="center">
  <img src="https://github.com/Kamilbur/Concurrency-theory-lab/blob/main/Resources/graph.png">
</p>
