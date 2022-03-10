import { browser, element, by } from 'protractor';

import NavBarPage from './../../page-objects/navbar-page';
import SignInPage from './../../page-objects/signin-page';
import KlasorTemelComponentsPage from './klasor-temel.page-object';
import KlasorTemelUpdatePage from './klasor-temel-update.page-object';
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

describe('KlasorTemel e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let klasorTemelComponentsPage: KlasorTemelComponentsPage;
  let klasorTemelUpdatePage: KlasorTemelUpdatePage;
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
    klasorTemelComponentsPage = new KlasorTemelComponentsPage();
    klasorTemelComponentsPage = await klasorTemelComponentsPage.goToPage(navBarPage);
  });

  it('should load KlasorTemels', async () => {
    expect(await klasorTemelComponentsPage.title.getText()).to.match(/Klasor Temels/);
    expect(await klasorTemelComponentsPage.createButton.isEnabled()).to.be.true;
  });

  it('should create and delete KlasorTemels', async () => {
    const beforeRecordsCount = (await isVisible(klasorTemelComponentsPage.noRecords))
      ? 0
      : await getRecordsCount(klasorTemelComponentsPage.table);
    klasorTemelUpdatePage = await klasorTemelComponentsPage.goToCreateKlasorTemel();
    await klasorTemelUpdatePage.enterData();
    expect(await isVisible(klasorTemelUpdatePage.saveButton)).to.be.false;

    expect(await klasorTemelComponentsPage.createButton.isEnabled()).to.be.true;
    await waitUntilDisplayed(klasorTemelComponentsPage.table);
    await waitUntilCount(klasorTemelComponentsPage.records, beforeRecordsCount + 1);
    expect(await klasorTemelComponentsPage.records.count()).to.eq(beforeRecordsCount + 1);

    await klasorTemelComponentsPage.deleteKlasorTemel();
    if (beforeRecordsCount !== 0) {
      await waitUntilCount(klasorTemelComponentsPage.records, beforeRecordsCount);
      expect(await klasorTemelComponentsPage.records.count()).to.eq(beforeRecordsCount);
    } else {
      await waitUntilDisplayed(klasorTemelComponentsPage.noRecords);
    }
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
