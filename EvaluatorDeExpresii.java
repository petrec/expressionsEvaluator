package expressionsEvaluator;

import java.util.Stack;

public class EvaluatorDeExpresii {
	
	public static boolean isNumeric(String input) {
		try {
			Integer.parseInt(input);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	public static String shuntingYard(String input){
		String formaPostFixata = "";
		Stack<String> stivaOperatori = new Stack<>();

		String caracter = "";
		for (int i = 0; i < input.length(); i++) {
			caracter = String.valueOf(input.charAt(i));
			if (isNumeric(caracter)) {
				formaPostFixata += caracter;
			} else if (caracter.equals("(")) {
				stivaOperatori.add(caracter);
			} else if (caracter.equals(")")) {
				if (stivaOperatori.peek().equals("(")){
					stivaOperatori.pop();
				} else {
					while ("+-*/^".contains(stivaOperatori.peek()) && stivaOperatori != null
							&& !stivaOperatori.empty() && !stivaOperatori.peek().equals("(")) {
						caracter = stivaOperatori.pop();
						formaPostFixata += caracter;
						if (stivaOperatori == null || stivaOperatori.empty()) {
							System.err.println("Expresia avea paranteze gresite!!!");
							caracter = "stop";
							break;
						} else if (stivaOperatori.peek().equals("(")){
							stivaOperatori.pop();
							break;
						}
					}
				}
				if (stivaOperatori != null && !stivaOperatori.empty() && stivaOperatori.peek().equals("(")){
					stivaOperatori.pop();
				}
			} else if (!"+-*/^".contains(caracter)){
				System.err.println("Caracter nepermis!");
			} else {
				String input2 = "";
				if (caracter.equals("^")) {
					stivaOperatori.add(caracter);
				} else if ("*/".contains(caracter)) {
					while (stivaOperatori != null && !stivaOperatori.empty() && !"*/".contains(stivaOperatori.peek())) {
						if ("+-".contains(stivaOperatori.peek())) {
							break;
						}
						input2 = stivaOperatori.pop();
						formaPostFixata += input2;
					}
					if (stivaOperatori != null && !stivaOperatori.empty() && "*/".contains(stivaOperatori.peek())) {
						input2 = stivaOperatori.pop();
						formaPostFixata += input2;
					}
					stivaOperatori.add(caracter);
				} else if ("+-".contains(caracter)) {
					while (stivaOperatori != null && !stivaOperatori.empty() && "^".contains(stivaOperatori.peek())) {
						input2 = stivaOperatori.pop();
						formaPostFixata += input2;
					} while (stivaOperatori != null && !stivaOperatori.empty() && "*/".contains(stivaOperatori.peek())) {
						input2 = stivaOperatori.pop();
						formaPostFixata += input2;
					} while (stivaOperatori != null && !stivaOperatori.empty() && !stivaOperatori.peek().equals("(")) {
						caracter = stivaOperatori.pop();
						formaPostFixata += caracter;
					}
					stivaOperatori.add(caracter);
				}
			}
		}
		while (!stivaOperatori.empty()) {
			caracter = stivaOperatori.pop();
			formaPostFixata += caracter;
		}
		return formaPostFixata;
	}
	
	public static String postFixEval(String input) {
		Stack<String> stivaOperanzi = new Stack<>();
		int result = 0;
		String caracter = "";
		int op1 = 0;
		int op2 = 0;
		
		for (int i = 0; i < input.length(); i++) {
			caracter = String.valueOf(input.charAt(i));
			if (isNumeric(caracter)) {
				stivaOperanzi.add(caracter);
			} else {
				if (stivaOperanzi != null && !stivaOperanzi.isEmpty()) {
					if (isNumeric(stivaOperanzi.peek())){
						op1 = Integer.parseInt(stivaOperanzi.pop());
						if (!isNumeric(stivaOperanzi.peek())){
							System.out.println("Expresia postfixata este incorecta!");
							break;
						}
						op2 = Integer.parseInt(stivaOperanzi.pop());
					} else {
						System.out.println("Expresia postfixata este incorecta!");
						break;
					}
					result = calculeaza(op1, op2, caracter);
					stivaOperanzi.add(String.valueOf(result));
				}
			}
		}
		return stivaOperanzi.pop();
	}
	
	static int calculeaza(int op1, int op2, String str) {
		int result = 0;
		switch(str) {
		case "^":
			result = (int) Math.pow(op2, op1);
			break;
		case "*":
			result = op1 * op2;
			break;
		case "/":
			result = op2 / op1;
			break;
		case "+":
			result = op1 + op2;
			break;
		case "-":
			result = op2 - op1;
			break;
		}
		return result;
	}
	
	
	public static void main(String[] args) {
		//3+(2+1)*2^3^2-8/(5-1*2/2)
		//3 2 1 + 2 3 2 ^ ^ * + 8 5 1 2 * 2 / - / -
		
		//(2+1)*2^3-8(5-1*2/2)
		//2 1 + 2 3 ^ * 8 5 1 2 * 2 / - -
		System.out.println(shuntingYard("3+(2+1)*2^3^2-8/(5-1*2/2)"));
		System.out.println(postFixEval("321+232^^*+8512*2/-/-"));
		

	}

}
