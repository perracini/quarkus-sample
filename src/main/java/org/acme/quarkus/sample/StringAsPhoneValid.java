package org.acme.quarkus.sample;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
 
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/*As anotações dão as seguintes características:

@Target({FIELD}): A anotação apenas pode ser utilizada em fields, ou seja, em variáveis globais da classe;
@Retention(RUNTIME): Essa validação é apenas em tempo de execução;
@Constraint(validatedBy = StringAsPhoneValidator.class): Define que a classe StringAsPhoneValidator será responsável pela implementação da validação.
Os atributos message, groups, payload e value são os padrões das anotações de Bean Validation, onde tem os seguintes objetivos:

message: A mensagem emitida em caso de erro na validação;
groups: Para utilização de grupo de validações de Bean Validation;
payloads: Para configurar o grau do erro de validação;
value: É a propriedade que recebe o valor do atributo inserido.
*/

@Target({FIELD})
@Retention(RUNTIME)
@Constraint(validatedBy = StringAsPhoneValidator.class)
public @interface StringAsPhoneValid {
	
    String message() default "Valor inválido";
    
    Class<?>[] groups() default {};
 
    Class<? extends Payload>[] payload() default {};
 
    String value() default "";

}
