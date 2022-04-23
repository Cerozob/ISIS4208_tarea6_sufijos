import random
import string
import sys
import pathlib

maxQueryLength = 100


def generate_text(length: int, alphabet: str) -> str:
    return "".join(random.choices(alphabet, k=length))


def generate_queries(n: int, alphabet: str, queryLength) -> str:
    queries = []
    for i in range(n):
        text = "".join(random.choices(alphabet, k=random.randint(1, queryLength)))
        queries.append(text+"\n")
    return queries


def gen_queries():
    if len(sys.argv) != 5:
        print("Usage: python3 textGenerator.py <path1> <path 2> <textLength> <numberOfQueries>")
        sys.exit(1)
    else:
        path1 = pathlib.Path(sys.argv[1])
        path2 = pathlib.Path(sys.argv[2])
        textLength = int(sys.argv[3])
        numberOfQueries = int(sys.argv[4])
        textalphabet = string.ascii_letters+string.digits+"\n"
        queryAlphabet = textalphabet.replace("\n", "")
        text = generate_text(textLength, textalphabet)
        queries = generate_queries(numberOfQueries, queryAlphabet, maxQueryLength)
        if not path1.exists():
            path1.touch()
        if not path2.exists():
            path2.touch()
        with open(path1, "w+") as f:
            f.write(text)
        with open(path2, "w+") as f:
            f.writelines(queries)
        print("Text 1 saved in: " + str(path1))
        print("Text 2 saved in: " + str(path2))


filenames = ["text100K.txt", "text1M.txt", "text10M.txt", "text10K.txt"]
queryNames = ["queries1K.txt", "queries10K.txt", "queries100K.txt", "queries1M.txt"] 

if __name__ == "__main__":
    gen_queries()
# sys.argv[1] = filenames[0]
# sys.argv[2] = queryNames[0]
# sys.argv[3] = 100000
# sys.argv[4] = 1000
# gen_queries()
# sys.argv[1] = filenames[1]
# sys.argv[2] = queryNames[1]
# sys.argv[3] = 1000000
# sys.argv[4] = 10000
# gen_queries()
# sys.argv[1] = filenames[2]
# sys.argv[2] = queryNames[2]
# sys.argv[3] = 10000000
# sys.argv[4] = 100000
# gen_queries()
# sys.argv[1] = filenames[3]
# sys.argv[2] = queryNames[3]
# sys.argv[3] = 10000
# sys.argv[4] = 1000000
# gen_queries()
