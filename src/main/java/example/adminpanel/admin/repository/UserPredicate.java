package example.adminpanel.admin.repository;


import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;

import example.adminpanel.core.dto.QUser;
import example.adminpanel.core.web.dto.AddressFilter;
import example.adminpanel.core.web.dto.UserFilter;

import static java.util.Objects.isNull;
import static org.springframework.util.StringUtils.isEmpty;

public class UserPredicate {

    public Predicate filterPredicate(UserFilter userFilter) {
        QUser qUser = new QUser("user");
        BooleanExpression predicate = null;
        if (!isEmpty(userFilter.getLogin())) {
            predicate = qUser.login.containsIgnoreCase(userFilter.getLogin());
        }
        if (!isEmpty(userFilter.getFirstName())) {
            predicate = setOrUpdatePredicate(predicate, qUser.firstName.startsWithIgnoreCase(userFilter.getFirstName()));
        }
        if (!isEmpty(userFilter.getLastName())) {
            predicate = setOrUpdatePredicate(predicate, qUser.lastName.startsWithIgnoreCase(userFilter.getLastName()));
        }
        if (!isNull(userFilter.getRole())) {
            predicate = setOrUpdatePredicate(predicate, qUser.role.eq(userFilter.getRole()));
        }
        if (!isNull(userFilter.getBirthday())) {
            if (userFilter.getBirthday().hasLowerBound()) {
                predicate = setOrUpdatePredicate(
                        predicate,
                        qUser.birthday.goe(userFilter.getBirthday().lowerEndpoint())
                );
            }
            if (userFilter.getBirthday().hasUpperBound()) {
                predicate = setOrUpdatePredicate(
                        predicate,
                        qUser.birthday.loe(userFilter.getBirthday().upperEndpoint())
                );
            }
        }
        if (!userFilter.getAddressFilter().isFilterEmpty()) {
            predicate = filterAddressPredicate(predicate, qUser, userFilter.getAddressFilter());
        }
        return predicate;
    }

    private BooleanExpression filterAddressPredicate(BooleanExpression predicate, QUser qUser, AddressFilter addressFilter) {
        if (!isEmpty(addressFilter.getCountry())) {
            predicate = setOrUpdatePredicate(predicate, qUser.address.country.startsWithIgnoreCase(addressFilter.getCountry()));
        }
        if (!isEmpty(addressFilter.getCity())) {
            predicate = setOrUpdatePredicate(predicate, qUser.address.city.startsWithIgnoreCase(addressFilter.getCity()));
        }
        return predicate;
    }

    private BooleanExpression setOrUpdatePredicate(BooleanExpression predicate, BooleanExpression nextPredicate) {
        if (predicate == null) {
            return nextPredicate;
        } else {
            return predicate.and(nextPredicate); //(nextPredicate);
        }
    }

}
