TopSecret README

A command-line utility for viewing and deciphering mission data files.

Team:
Role | Member                    | Responsibility
A    | Bradley Ayieko (pgc4zz)   | User Interface / CLI
B    | Karim (qna3tx)            | File Handler
C    | Tugce Yaylaci (vgy2hp)    | Program Control
D    | Jianwen Ding (yrb8xt)     | Cipher / Decryption

Commands:
java topsecret              List all available files in
                            01 file.txt
                            02 file2.txt
                            03 file3.txt
java topsecret <nn>         Display file contents (deciphered with default substitution key)
java topsecret <nn> <key>   Display file contents (deciphered with alternate substitution key)
