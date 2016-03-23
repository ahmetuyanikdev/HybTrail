/**
 * 
 */
package de.hybris.platform.cuppytrail.daos.impl;

import de.hybris.platform.cuppytrail.daos.StadiumDAO;

import java.util.List;


/**
 * @author luca.gennari
 * 
 */
@Component(value = "stadiumDAO")
public class DefaultStadiumDAO implements StadiumDAO
{
	@Autowired
	private FlexibleSearchService flexibleSearchService;

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.hybris.platform.cuppytrail.daos.StadiumDAO#findStadiums()
	 */
	@Override
	public List<StadiumModel> findStadiums()
	{
		// Build a query for the flexible search.
		final String queryString = //
		"SELECT {p:" + StadiumModel.PK + "} , " + " {p:" + StadiumModel.STADIUMTYPE + "}" + "FROM {" + StadiumModel._TYPECODE
				+ " AS p} ";

		final FlexibleSearchQuery query = new FlexibleSearchQuery(queryString);

		// Note that we could specify paginating logic by providing a start and count variable (commented out below)
		// This can provide a safeguard against returning very large amounts of data, or hogging the database when there are
		// for example millions of items being returned.
		// As we know that there are only a few persisted stadiums in this use case we do not need to provide this.

		//query.setStart(start);
		//query.setCount(count);

		// Return the list of StadiumModels.
		return flexibleSearchService.<StadiumModel> search(query).getResult();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.hybris.platform.cuppytrail.daos.StadiumDAO#findStadiumsByCode(java.lang.String)
	 */
	@Override
	public List<StadiumModel> findStadiumsByCode(final String code)
	{
		final String queryString = //
		"SELECT {p:" + StadiumModel.PK + "}" //
				+ "FROM {" + StadiumModel._TYPECODE + " AS p} "//
				+ "WHERE " + "{p:" + StadiumModel.CODE + "}=?code ";

		final FlexibleSearchQuery query = new FlexibleSearchQuery(queryString);
		query.addQueryParameter("code", code);

		return flexibleSearchService.<StadiumModel> search(query).getResult();
	}

}
