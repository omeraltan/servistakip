import { browser, element, by } from 'protractor';

import NavBarPage from './../../page-objects/navbar-page';
import SignInPage from './../../page-objects/signin-page';
import IrtibatTemelComponentsPage from './irtibat-temel.page-object';
import IrtibatTemelUpdatePage from './irtibat-temel-update.page-object';
import {
  waitUntilDisplayed,
  waitUntilAnyDisplayed,
  click,
  getRecordsCount,
  waitUntilHidden,
  waitUntilCount,
  isVisible,
} from '../../util/utils';

const expect = chai.expect;

describe('IrtibatTemel e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let irtibatTemelComponentsPage: IrtibatTemelComponentsPage;
  let irtibatTemelUpdatePage: IrtibatTemelUpdatePage;
  const username = process.env.E2E_USERNAME ?? 'admin';
  const password = process.env.E2E_PASSWORD ?? 'admin';

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.waitUntilDisplayed();
    await signInPage.username.sendKeys(username);
    await signInPage.password.sendKeys(password);
    await signInPage.loginButton.click();
    await signInPage.waitUntilHidden();
    await waitUntilDisplayed(navBarPage.entityMenu);
    await waitUntilDisplayed(navBarPage.adminMenu);
    await waitUntilDisplayed(navBarPage.accountMenu);
  });

  beforeEach(async () => {
    await browser.get('/');
    await waitUntilDisplayed(navBarPage.entityMenu);
    irtibatTemelComponentsPage = new IrtibatTemelComponentsPage();
    irtibatTemelComponentsPage = await irtibatTemelComponentsPage.goToPage(navBarPage);
  });

  it('should load IrtibatTemels', async () => {
    expect(await irtibatTemelComponentsPage.title.getText()).to.match(/Irtibat Temels/);
    expect(await irtibatTemelComponentsPage.createButton.isEnabled()).to.be.true;
  });

  it('should create and delete IrtibatTemels', async () => {
    const beforeRecordsCount = (await isVisible(irtibatTemelComponentsPage.noRecords))
      ? 0
      : await getRecordsCount(irtibatTemelComponentsPage.table);
    irtibatTemelUpdatePage = await irtibatTemelComponentsPage.goToCreateIrtibatTemel();
    await irtibatTemelUpdatePage.enterData();
    expect(await isVisible(irtibatTemelUpdatePage.saveButton)).to.be.false;

    expect(await irtibatTemelComponentsPage.createButton.isEnabled()).to.be.true;
    await waitUntilDisplayed(irtibatTemelComponentsPage.table);
    await waitUntilCount(irtibatTemelComponentsPage.records, beforeRecordsCount + 1);
    expect(await irtibatTemelComponentsPage.records.count()).to.eq(beforeRecordsCount + 1);

    await irtibatTemelComponentsPage.deleteIrtibatTemel();
    if (beforeRecordsCount !== 0) {
      await waitUntilCount(irtibatTemelComponentsPage.records, beforeRecordsCount);
      expect(await irtibatTemelComponentsPage.records.count()).to.eq(beforeRecordsCount);
    } else {
      await waitUntilDisplayed(irtibatTemelComponentsPage.noRecords);
    }
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
