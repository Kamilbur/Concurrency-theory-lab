from lab10.trace import Trace


def main():
    # Input format example
    # Alphabet
    alphabet_letters = "abcdef"
    # Independence relation
    independence_relation_pairs = "ad, da, bf, fb, ac, ca"
    # Word representing a trace
    word = "afadbdfba"

    alphabet = set(alphabet_letters)
    independence_relation = set(independence_relation_pairs.replace(' ', '').split(','))

    trace = Trace(alphabet, independence_relation, word)

    pretty_dependence_format = ', '.join(['(' + pair[0] + ', ' + pair[1] + ')' for pair in trace.dependence])
    pretty_dependence_format = '{' + pretty_dependence_format + '}'
    print(f"dependency relation:        {pretty_dependence_format}")
    print(f"trace as equivalence class: {trace.trace_equiv_class()}")
    print(f"foata normal form:          {trace.foata_normal}")
    print(f"foata normal from graph:    {trace.graph_to_foata()}")

    trace.save_graph_to_file()


if __name__ == '__main__':
    main()
