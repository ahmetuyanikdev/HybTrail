/**
 * 
 */
package de.hybris.platform.cuppytrail.validation;

import de.hybris.platform.cuppy.model.GroupModel;
import de.hybris.platform.cuppy.model.MatchBetModel;
import de.hybris.platform.cuppy.model.MatchModel;
import de.hybris.platform.cuppy.model.PlayerModel;
import de.hybris.platform.servicelayer.ServicelayerTransactionalTest;
import de.hybris.platform.servicelayer.model.ModelService;
import de.hybris.platform.validation.exceptions.HybrisConstraintViolation;
import de.hybris.platform.validation.services.ValidationService;

import java.util.Set;

import javax.annotation.Resource;

import org.junit.Assert;
import org.junit.Test;


/**
 * @author luca.gennari
 * 
 */
public class ValidationTest extends ServicelayerTransactionalTest
{
	@Resource
	private ModelService modelService;

	@Resource
	ValidationService validationService;

	@Test
	public void testMatchBetConstraints()
	{
		final MatchBetModel matchBetModel = modelService.create(MatchBetModel.class);
		matchBetModel.setPlayer((PlayerModel) modelService.create(PlayerModel.class));
		matchBetModel.setMatch((MatchModel) modelService.create(MatchModel.class));
		matchBetModel.setHomeGoals(100);
		matchBetModel.setGuestGoals(200);

		final Set<HybrisConstraintViolation> violations = validationService.validate(matchBetModel);

		Assert.assertNotNull("The violation set cannot be null", violations);
		Assert.assertEquals("There should be two constraint violations", 2, violations.size());

		for (final HybrisConstraintViolation hybrisConstraintViolation : violations)
		{
			System.out.println(hybrisConstraintViolation.getProperty() + ":" + hybrisConstraintViolation.getLocalizedMessage());
		}
	}

	@Test
	public void testNotEmptyConstraint()
	{
		final GroupModel groupModel = modelService.create(GroupModel.class);
		final Set<HybrisConstraintViolation> violations = validationService.validate(groupModel);

		Assert.assertNotNull("The violation set cannot be null", violations);
		Assert.assertEquals("There should be one constraint violation", 1, violations.size());

		for (final HybrisConstraintViolation hybrisConstraintViolation : violations)
		{
			System.out.println(hybrisConstraintViolation.getProperty() + ": " + hybrisConstraintViolation.getLocalizedMessage());
		}
	}
}
