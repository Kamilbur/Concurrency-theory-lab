import os
import settings
import graphviz

os.environ["PATH"] += os.pathsep + settings.graphviz_path


class Trace:
    __MARKER = chr(int('E000', base=16))  # private use area of unicode

    def __init__(self, alphabet, independence, word):
        # attributes
        self.alphabet = alphabet
        self.independence = independence
        self.word = word
        self.dependence = None
        self.foata_normal = None
        self.graph_edges = None
        self.graph_labels = None

        # input correctness check
        for letter in self.word:
            if letter not in self.alphabet:
                raise Exception(f'Letter {letter} of word not in alphabet.')

        for pair in self.independence:
            if (pair[1] + pair[0]) not in self.independence:
                raise Exception('Independence relation is not symmetric.')

        for letter in self.alphabet:
            if 2 * letter in self.alphabet:
                raise Exception('Independence relation is not anti-reflexive.')

        # compute other trace forms
        self.dependence = self.calculate_dependence()
        self.foata_normal = self.compute_Foata_Normal_Form()
        self.compute_dependency_graph()

    def calculate_dependence(self):
        dependence = set()
        for first in self.alphabet:
            for second in self.alphabet:
                pair = first + second
                if pair not in self.independence:
                    dependence.add(pair)

        return dependence

    def trace_equiv_class(self):
        old = {self.word}
        new = set()

        while True:
            for word in old:
                for i in range(len(word) - 1):
                    if word[i:i + 2] in self.independence:
                        new.add(
                            word[:i] + word[i + 1] + word[i] + word[i + 2:])
            if old == new:
                break
            else:
                old = new.copy()

        return old

    # noinspection PyPep8Naming
    def compute_Foata_Normal_Form(self):
        """
            There is a stack for every letter from the alphabet.

            Algorithm goes as follows:

            0) Read the word in reversed order and for each letter
               put it on stack assigned to this letter and on every stack
               assigned to letters in dependency relation with currently
               processed letter put marker.

            1) If all stacks are empty, then end.

            2) Let S be the set of letters, which are at the top of stacks.
               For every letter that is in dependency relation with any
               letter from S, remove element from the top of the stack
               assigned to this letter. Go to step 1).

        """
        stacks = {letter: list() for letter in self.alphabet}
        stacks_sizes = {letter: 0 for letter in self.alphabet}

        # Prepare all stacks corresponding to given letter.
        for letter in reversed(self.word):
            for other_letter in self.alphabet:
                pair = letter + other_letter
                if pair in self.dependence:
                    stacks_sizes[other_letter] = stacks_sizes[other_letter] + 1
                    if letter != other_letter:
                        stacks[other_letter] += [self.__MARKER]
                    else:
                        stacks[letter] += [letter]

        normal_form = ''
        step = []

        while True:
            # Get letters from tops of stacks.
            for stack in stacks.values():
                if stack:
                    if stack[-1] != self.__MARKER:
                        step += [stack[-1]]

            # Remove dependent elements.
            for element in step:
                for letter in self.alphabet:
                    pair = element + letter
                    if pair in self.dependence:
                        stacks[letter].pop()

            if step:
                normal_form += '(' + ''.join(sorted(step)) + ')'
                step = []
            else:
                break

        return normal_form

    def compute_dependency_graph(self):
        labels = []
        edges = []

        for i in range(len(self.word)):
            labels += [self.word[i]]
            for j in range(len(labels) - 1):
                pair = labels[j] + self.word[i]
                if pair in self.dependence:
                    edges += [(j, i)]

        self.graph_edges = edges
        self.graph_labels = labels

        # delete redundant edges
        for v in range(len(labels)):
            self.__bfs_redundant_removal(v)

    def __bfs_redundant_removal(self, source):
        queue = [source]

        reachability_counter = [0] * len(self.graph_labels)

        while queue:
            node, queue = queue[0], queue[1:]

            for edge in self.graph_edges:
                if edge[0] == node:
                    if reachability_counter[edge[1]] == 0:
                        queue += [edge[1]]
                    reachability_counter[edge[1]] += 1

        self.graph_edges = [edge for edge in self.graph_edges if not (
                edge[0] == source and reachability_counter[edge[1]] > 1
        )]

    def __topological_sort(self):
        nodes_list = []
        visited = [False] * len(self.graph_labels)

        for v in list(range(len(self.graph_labels))):
            if not visited[v]:
                self.__dfs(v, nodes_list, visited)

        return list(reversed(nodes_list))

    def __dfs(self, node, nodes_list, visited):
        visited[node] = True

        for edge in self.graph_edges:
            if edge[0] == node and not visited[edge[1]]:
                self.__dfs(edge[1], nodes_list, visited)

        nodes_list.append(node)

    def graph_to_foata(self):
        topological_nodes = self.__topological_sort()
        max_dist = [0] * len(topological_nodes)

        for v in topological_nodes:
            for edge in self.graph_edges:
                if edge[1] == v and max_dist[v] <= max_dist[edge[0]]:
                    max_dist[v] = max_dist[edge[0]] + 1

        distances = set(max_dist)
        classes = ['' for _ in range(len(distances))]
        for dist in distances:
            class_elements = [self.graph_labels[i] for i in
                              range(len(max_dist))
                              if max_dist[i] == dist]
            classes[dist] = ''.join(sorted(class_elements))

        return ''.join(list(map(lambda x: '(' + x + ')', classes)))

    def save_graph_to_file(self, filename='graph_results/graph', file_format='jpeg'):
        """ Save graph to file in graphviz format and as an image

        :param filename: string containing a name of file to which the graph will be saved
        :param file_format:  string with format information e.g. 'png', 'svg', 'jpeg', ...
        :return:
        """
        graph = graphviz.Digraph('G', filename=filename, format=file_format)
        for i, label in enumerate(self.graph_labels):
            graph.node(str(i), label=label)

        for edge in self.graph_edges:
            graph.edge(str(edge[0]), str(edge[1]))

        try:
            graph.render()
        except graphviz.ExecutableNotFound as e:
            print("Make sure you have Graphviz executables (not only python library) and that they are on systems' "
                  "PATH.\n"
                  "If you have executables, the only thing to do is to put path to them into settings.py.")
            print("Exception message:")
            print(e)

    def __str__(self):
        return '[' + self.word + ']'

    def __repr__(self):
        return 'Trace(' + self.word + ')'
