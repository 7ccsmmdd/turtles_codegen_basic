/*
 * generated by Xtext 2.25.0
 */
package uk.ac.kcl.inf.mdd3.scoping

import org.eclipse.xtext.scoping.impl.AbstractDeclarativeScopeProvider
import uk.ac.kcl.inf.mdd3.turtles.IntVarExpression
import org.eclipse.emf.ecore.EReference
import org.eclipse.xtext.scoping.IScope
import uk.ac.kcl.inf.mdd3.turtles.IntExpression
import uk.ac.kcl.inf.mdd3.turtles.TurtleProgram
import uk.ac.kcl.inf.mdd3.turtles.VariableDeclaration
import uk.ac.kcl.inf.mdd3.turtles.LoopStatement

import static org.eclipse.xtext.scoping.Scopes.*

/** 
 * This class contains custom scoping description.
 * See https://www.eclipse.org/Xtext/documentation/303_runtime_concepts.html#scoping
 * on how and when to use it.
 */
class TurtlesScopeProvider extends AbstractDeclarativeScopeProvider {
	def IScope scope_IntVarExpression_var(IntVarExpression context, EReference ref) {
		context.visibleVariablesScope
	}

	dispatch def IScope visibleVariablesScope(IntExpression ip) {
		ip.eContainer.visibleVariablesScope
	}

	dispatch def IScope visibleVariablesScope(TurtleProgram tp) {
		scopeFor(tp.statements.filter(VariableDeclaration))
	}

	dispatch def IScope visibleVariablesScope(LoopStatement ls) {
		ls.eContainer.internalVisibleVariablesScope
	}

	dispatch def IScope internalVisibleVariablesScope(TurtleProgram tp) {
		scopeFor(tp.statements.filter(VariableDeclaration))
	}

	dispatch def IScope internalVisibleVariablesScope(LoopStatement ls) {
		scopeFor(ls.statements.filter(VariableDeclaration), ls.eContainer.internalVisibleVariablesScope)
	}

}
