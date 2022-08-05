# VMTranslator-using-Java
This is a Virtual Machine Language Translator made using Java which Translates VM language code into assembly code.<br>
This VM Translator has three modules:
  1. <b>VMTranslator.java</b> which is the top-most/ main module which drives the process.
  2. <b>Parser.java</b> which reads a VM command, parses the command into its lexical components, and provides convenient access to these components. It also removes all the white-spaces and comments.
  3. <b>CodeWriter.java</b> which generates the actual assembly codes by translating the VM codes.
